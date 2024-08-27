package homework;

import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> customerMap = new TreeMap<>();

    private Map.Entry<Customer, String> getEntry(Map.Entry<Customer, String> entry) {
        return Map.entry(new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores()), entry.getValue());
    }

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> firstEntry = customerMap.firstEntry();
        return firstEntry == null ? null : getEntry(firstEntry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> higherEntry = customerMap.higherEntry(customer);
        return higherEntry == null ? null : getEntry(higherEntry);
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }
}