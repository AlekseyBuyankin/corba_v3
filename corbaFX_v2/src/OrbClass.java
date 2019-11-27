import NotePadApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

public class OrbClass {

    private static NotePad notePadImpl;

    public static void init(String[] args) {
        {
            try {
                // create and initialize the ORB
                ORB orb = ORB.init(args, null);

                // get the root naming context
                org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");

                // Use NamingContextExt instead of NamingContext.
                // This is part of the Interoperable naming Service.
                NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

                // resolve the Object Reference in Naming
                String name = "Hello";
                notePadImpl = NotePadHelper.narrow(ncRef.resolve_str(name));

                System.out.println("Obtained a handle on server object: " + notePadImpl);

            } catch (Exception e) {
                System.out.println("ERROR : " + e);
                e.printStackTrace(System.out);
            }
        }
    }

    public static boolean addNote(String string, int n) {
        return notePadImpl.addNote(string, n);
    }

    public static void addNullNote() {
        notePadImpl.addNullNote();
    }

    public static boolean showNotes() {
        return notePadImpl.showNotes();
    }

    public static String[] getNotes() {
        return notePadImpl.getNotes();
    }

    public static boolean deleteNote(int i) {
        return notePadImpl.deleteNote(i);
    }
}
