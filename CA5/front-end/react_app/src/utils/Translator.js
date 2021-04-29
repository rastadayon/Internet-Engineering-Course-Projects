
export default class Translator {
    static toFa(enText) {
        switch (enText) {
            case "count of this offer is not enough for you to submit":
                return "موجودی غذای درخواستی کافی نیست. لطفا تعداد را کاهش دهید.";
            case "user cart is empty":
                return "سبد خرید شما خالی است";
            case "Error: you have some food from another restaurant, then you can not add foods from another restaurant to your cart":
                return "سفارش شما از رستوران قبلی تمام نشده است";
            case "credit is not enough for finalizing your order":
                return "اعتبار شما کافی نیست";
            case "SEARCHING":
                return "در جست‌و‌جوی پیک";
            case "DELIVERING":
                return "پیک در مسیر"
            case "DELIVERED":
                return "مشاهده فاکتور"

            default:
                return enText
        }
    }
}