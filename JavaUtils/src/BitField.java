/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 16/05/13
 * Time: 2:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class BitField {
    /*  Container for the enum */
    public enum StatusFlag {           // The status
        STATUS_1,
        STATUS_2,
        STATUS_3;

        private long statusFlagValue;  //  The bit field

        StatusFlag(long status) {
            statusFlagValue = status;
        }

        public long getStatusFlagValue() {
            return 1 << this.ordinal();
        }

    }



/*Translate status number to a flag*/

    public EnumSet<BitField> getStatusFlags(long statusValue){
        EnumSet
    }

}
