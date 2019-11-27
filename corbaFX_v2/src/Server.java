import NotePadApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import java.util.ArrayList;
import java.util.Arrays;

class NotePadImpl extends NotePadPOA {
    private ORB orb;

    private static ArrayList<String> arrayList = new ArrayList<String>();

    void setORB(ORB orb_val) {
        orb = orb_val;
    }

    @Override
    public boolean addNote(String string, int n) {
        try {
            arrayList.set(n, string);

            System.out.println("String added: \n" + string + "\n");

            return true;

        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
            return false;
        }
    }

    @Override
    public void addNullNote() {
        try {
            arrayList.add("");

        } catch (Exception e) {
            System.out.println("error: " + e);
            e.printStackTrace(System.out);
        }
    }

    @Override
    public boolean showNotes() {
        try {
            StringBuilder stringBuilder = new StringBuilder();

            for (String s : arrayList) {
                stringBuilder.append(s);
                stringBuilder.append("\n");
            }

            System.out.println("All notes: \n" + stringBuilder.toString() + "\n");

            return true;

        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
            return false;
        }
    }

    @Override
    public boolean deleteNote(int num) {
        if (num < arrayList.size() && arrayList.size() > 0) {
            System.out.println("String " + arrayList.get(num) + " deleted\n");

            arrayList.remove(num);
            return true;
        }
        return false;
    }

    // implement shutdown() method
    public void shutdown() {
        orb.shutdown(false);
    }

    @Override
    public String[] getNotes() {
        return fromArrayListToString();
    }

    private String[] fromArrayListToString() {
        String[] strings = new String[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++) {
            String s = arrayList.get(i);
            strings[i] = s;
        }
        return strings;
    }
}

public class Server {
    public static void main(String[] args) {
        try {
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant and register it with the ORB
            NotePadImpl notePadImpl = new NotePadImpl();
            notePadImpl.setORB(orb);

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(notePadImpl);
            NotePad href = NotePadHelper.narrow(ref);

            // get the root naming context
            // NameService invokes the name service
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // bind the Object Reference in Naming
            String name = "Hello";
            NameComponent[] path = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("NotePad server ready and waiting ...");

            // wait for invocations from clients
            orb.run();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

        System.out.println("NotePad server exiting ...");
    }
}
