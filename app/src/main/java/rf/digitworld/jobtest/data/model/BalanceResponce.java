package rf.digitworld.jobtest.data.model;


import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BalanceResponce {
    @Expose
    private int result_code;
    @Expose
    private String message;
    @Expose
    private List<Balance> balances;

    public BalanceResponce() {
    }

    public int getResult_code() {
        return result_code;
    }

    public List<Balance> getBalances() {
        if (balances!=null){
            return balances;
        }else{
            return new ArrayList<>();
        }

    }

    public String getMessage() {
        return message;
    }

    public class Balance {
        @Expose
        private String currency;
        @Expose
        private Double amount;

        public Balance() {
        }

        public String getCurrency() {
            return currency;
        }


        public Double getAmount() {
            return amount;
        }
        public String getCurrencyString() {
            String currencySymbol;
            switch (currency){
                case "RUB":
                    currencySymbol=amount+" руб.";
                    break;
                case "USD":
                    currencySymbol="\u0024"+amount;
                    break;
                case "KZT":
                    currencySymbol="\u20b8"+amount;
                    break;
                case "EUR":
                    currencySymbol="\u20ac"+amount;
                    break;
                default:
                    currencySymbol=amount+currency ;

            }
            return currencySymbol;


        }
    }

}
