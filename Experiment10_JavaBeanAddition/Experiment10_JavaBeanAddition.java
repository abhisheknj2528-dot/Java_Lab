import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.beans.Introspector;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;

class AdditionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private double firstNumber;
    private double secondNumber;
    private double sum;

    private final PropertyChangeSupport propertySupport;

    public AdditionBean() {
        this.firstNumber = 0.0;
        this.secondNumber = 0.0;
        this.sum = 0.0;
        this.propertySupport = new PropertyChangeSupport(this);
    }

    public double getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(double firstNumber) {
        double oldVal = this.firstNumber;
        this.firstNumber = firstNumber;
        propertySupport.firePropertyChange("firstNumber", oldVal, firstNumber);
        calculateSum();
    }

    public double getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(double secondNumber) {
        double oldVal = this.secondNumber;
        this.secondNumber = secondNumber;
        propertySupport.firePropertyChange("secondNumber", oldVal, secondNumber);
        calculateSum();
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        double oldSum = this.sum;
        this.sum = sum;
        propertySupport.firePropertyChange("sum", oldSum, sum);
    }

    public double calculateSum() {
        double result = this.firstNumber + this.secondNumber;
        setSum(result);
        return result;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "AdditionBean [firstNumber=" + firstNumber +
               ", secondNumber=" + secondNumber +
               ", sum=" + sum + "]";
    }
}

public class Experiment10_JavaBeanAddition {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   EXPERIMENT 10: JAVABEAN COMPONENT DEMONSTRATION");
        System.out.println("==================================================");

        System.out.println("\n[1] Instantiating AdditionBean via Default Constructor...");
        AdditionBean bean = new AdditionBean();

        bean.addPropertyChangeListener(evt -> {
            System.out.printf("    -> Event: Property '%s' mutated: %s -> %s\n",
                evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
        });

        System.out.println("\n[2] Setting firstNumber to 45.5...");
        bean.setFirstNumber(45.5);

        System.out.println("\n[3] Setting secondNumber to 24.5...");
        bean.setSecondNumber(24.5);

        System.out.println("\n[4] Accessing Bean Properties via Getters:");
        System.out.println("    First Number  : " + bean.getFirstNumber());
        System.out.println("    Second Number : " + bean.getSecondNumber());
        System.out.println("    Calculated Sum: " + bean.getSum());
        System.out.println("    Full State    : " + bean);

        System.out.println("\n[5] JavaBeans Introspection (Dynamic Component Analysis):");
        try {
            BeanInfo info = Introspector.getBeanInfo(AdditionBean.class, Object.class);
            System.out.println("    Discovered Component Properties:");
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                System.out.printf("     - Property: %-15s [Type: %s, Getter: %s, Setter: %s]\n",
                    pd.getName(),
                    pd.getPropertyType().getSimpleName(),
                    (pd.getReadMethod() != null ? pd.getReadMethod().getName() : "none"),
                    (pd.getWriteMethod() != null ? pd.getWriteMethod().getName() : "none"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n[6] Serialization Test (Verifying Serializable Contract):");
        File file = new File("AdditionBean_State.ser");
        try {

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(bean);
                System.out.println("    Saved bean state to " + file.getName());
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                AdditionBean restored = (AdditionBean) ois.readObject();
                System.out.println("    Restored bean from file: " + restored);
            }

            if (file.exists()) file.delete();
            System.out.println("    Serialization verification passed.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n==================================================");
        System.out.println("JavaBean Addition Component verified successfully.");
        System.out.println("==================================================");
    }
}
