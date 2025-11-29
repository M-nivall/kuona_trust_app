package com.example.Varsani.utils;

public class Urls {


    //public static String ipAddress = "http://192.168.35.242/kuonatrust/";
    public static String ipAddress = "https://e0ba16f98ac3.ngrok-free.app/kuonatrust/";

    private static final String ROOT_URL =ipAddress+ "android_files/";
    public static final String ROOT_URL_IMAGES =ipAddress+"upload_products/" ;

    public static final String ROOT_URL_ART_IMAGES =ROOT_URL+ "upload_art/";
    public static final String ROOT_URL_EXHIBITION_IMAGES =ROOT_URL+ "exhibitions/";
    public static final String ROOT_URL_WORKSHOP_IMAGES =ROOT_URL+ "workshops/";
    public static final String ROOT_URL_EXHIBITION_ARTS =ROOT_URL+ "exhibition_artworks/";

    public static  final String URL_PRINT=ipAddress+"print_pdf.php";
    public static  final String UEL_FEEDBACK=ROOT_URL+"client/get_feedback.php";
    public static  final String UEL_FEEDBACK_SEND=ROOT_URL+"client/send_feedback.php";


    public static  final String UEL_STAFF_SEND_FEEDBACK=ROOT_URL+"client/staff_sendfeedback.php";
    public static  final String UEL_STAFF_FEEDBACK=ROOT_URL+"client/getstafffeedback.php";

     //  products
    public static final String URL_ADD_CART=ROOT_URL + "client/add_to_cart.php";
    public static final String URL_GET_CART=ROOT_URL + "client/cart.php";
    public static final String URL_UPDATE_CART=ROOT_URL + "client/car_update.php";
    public static final String URL_REMOVE_ITEM=ROOT_URL + "client/cart_remove.php";

    //services
    public static final String URL_ADD_CART2=ROOT_URL + "client/add_to_cart2.php";
    public static final String URL_GET_CART2=ROOT_URL + "client/cart2.php";
    public static final String URL_REMOVE_BOOKING=ROOT_URL + "client/booking_remove.php";
    public static final String URL_SUBMIT_REQUEST = ROOT_URL+"client/submit_request.php";

    // shipping
    public static final String URL_GET_COUNTIES=ROOT_URL + "client/counties.php";
    public static final String URL_GET_TOWNS=ROOT_URL + "client/towns.php";
    public static final String URL_DELIVERY_DETAILS=ROOT_URL + "client/delivery_details.php";

    // checkout
    public static final String URL_GET_CHECKOUT_TOTAL=ROOT_URL + "client/checkout_cost.php";
    // user
    public static final String URL_REG = ROOT_URL +"client/register.php";
    public static final String URL_LOGIN= ROOT_URL+"client/login.php";
    public static final String URL_RESET = ROOT_URL + "client/forgotpass.php";
    public static final String URL_RESET2 = ROOT_URL + "client/resetpass.php";
//    SUPPLIERS
    public static final String URL_REG_SUPPLIERS= ROOT_URL+"supplier/reg_supplier.php";
    public static final String URL_MY_REQUESTS= ROOT_URL+"supplier/my_requests.php";
    public static final String URL_ACCEPT= ROOT_URL+"supplier/approve_items.php";
   // orders
    public static final String URL_SUBMIT_ORDER = ROOT_URL+"client/submit_order.php";
    public static final String URL_GET_ORDERS= ROOT_URL+"client/order_history.php";
    public static final String URL_GET_ORDER_ITEMS= ROOT_URL+"client/order_items.php";
    public static final String URL_GET_ORDER_ITEMS2= ROOT_URL+"client/order_items2.php";
    public static final String URL_MARK_DELIVERED= ROOT_URL+"client/mark_delivered.php";
    public static final String URL_MARK_REJECTED= ROOT_URL+"client/mark_rejected.php";
    public static final String URL_MARK_COMPLETE= ROOT_URL+"client/mark_completed.php";

    //invoices
    public static final String URL_GET_INVOICE= ROOT_URL+"client/invoice_history.php";
    public static final String URL_SUBMIT_INVOICE= ROOT_URL+"client/submit_invoice.php";

    //Staff
    public static final String URL_STAFF_LOGIN=ROOT_URL + "staff_login.php";
    //Finance
    public static final String URL_NEW_DONATIONS=ROOT_URL + "finance/new_donations.php";
    public static final String URL_RECEIVED_DONATIONS=ROOT_URL + "finance/received_donations.php";
    public static final String URL_APPROVE_DONATION=ROOT_URL + "finance/approve_donation.php";
    public static final String URL_RELEASE_DONATION=ROOT_URL + "finance/release_donation.php";
    public static final String URL_DISPATCHED_DONATIONS=ROOT_URL + "finance/dispatched_donations.php";
    public static final String URL_GET_CLIENT_ITEMS=ROOT_URL + "client_item.php";
    public static final String URL_APPROVED_ORDERS=ROOT_URL + "finance/approved_orders.php";
    public static final String URL_NEW_SERV_PAYMENTS=ROOT_URL + "finance/new_serv_payments.php";
    public static final String URL_APPROVE_SERV_PAYMENTS=ROOT_URL + "finance/approve_serv_payments.php";
    public static final String URL_APPROVED_SERV_PAYMENTS=ROOT_URL + "finance/approved_serv_payments.php";
    public static final String URL_SUPPLY_PAYMENTS=ROOT_URL + "finance/supply_payments.php";
    public static final String URL_SUPPLY_PAYMENTS2=ROOT_URL + "finance/supply_payments2.php";
    public static final String URL_PAY_SUPPLIER=ROOT_URL + "finance/pay_supplier.php";

    //shipping mrg
    public static final String URL_ORDERS_TO_SHIP=ROOT_URL + "ship_mrg/orders_to_ship.php";
    public static final String URL_GET_DRIVERS=ROOT_URL + "ship_mrg/get_drivers.php";
    public static final String URL_SHIP_ORDER=ROOT_URL + "ship_mrg/ship_order.php";
    public static final String URL_SHIPPING_ORDERS=ROOT_URL + "ship_mrg/shipping_orders.php";
    public static final String URL_APPROVE_TENDER=ROOT_URL + "ship_mrg/approve_tender.php";


    //Service   Manager
    public static final String URL_QUOTATION_REQUEST=ROOT_URL + "serv_mrg/quot_requests.php";
    public static final String URL_QUOTATION_ITEMS=ROOT_URL + "quot_items.php";
    public static final String URL_GET_TECHNICIANS=ROOT_URL + "serv_mrg/get_technicians.php";
    public static final String URL_ASSIGN_TECH=ROOT_URL + "serv_mrg/assign_tech.php";

    //technician
    public static final String URL_GET_ASSIGNED_SITES=ROOT_URL + "technician/assigned_orders.php";
    public static final String URL_GET_ASSIGNED_SERVICES=ROOT_URL + "technician/assigned_services.php";
    public static final String URL_SEND_QUOTATION=ROOT_URL + "technician/send_quotation.php";
    public static final String URL_CONFIRM_COMPLETION=ROOT_URL + "technician/confirm_completion.php";

    // Driver
    public static final String URL_GET_ASSIGNED_ORDERS=ROOT_URL + "driver/assigned_orders.php";
    public static final String URL_GET_MARKED_ORDERS=ROOT_URL + "driver/arrived_orders.php";
    public static final String URL_GET_DELIVERED_ORDERS=ROOT_URL + "driver/delivered_orders.php";
    public static final String URL_MARK_ORDER=ROOT_URL + "driver/mark_arrived.php";
    //Store mrg
    public static final String URL_GET_STOCK=ROOT_URL + "stock_mrg/stock.php";
    public static final String URL_ADD_STOCK=ROOT_URL + "stock_mrg/add_stock.php";
    public static final String URL_SUPPLIER=ROOT_URL + "stock_mrg/suppliers.php";
    public static final String URL_SEND_REQUEST=ROOT_URL + "stock_mrg/send_requests.php";
    public static final String URL_REQUESTS=ROOT_URL + "stock_mrg/request.php";
    public static final String URL_REQUESTMATERIALS=ROOT_URL + "stock_mrg/material_request.php";
    public static final String URL_APPROVE_MATERIALS=ROOT_URL + "ship_mrg/approve_materials.php";
    //Artist
    public static final String URL_COMPLETE_PROFILE = ROOT_URL + "artist/complete_profile.php/";
    public static final String URL_UPLOAD_ART = ROOT_URL + "artist/upload_art.php/";
    public static final String URL_APPLIED_EXHIBITIONS=ROOT_URL + "artist/exhibition_applications.php";
    public static final String URL_MY_DONATIONS=ROOT_URL + "artist/my_donations.php";
    public static final String URL_ADD_TO_WALLET=ROOT_URL + "artist/add_to_wallet.php";
    public static final String URL_WITHDRAW=ROOT_URL + "artist/withdraw.php";
    public static final String URL_MY_WALLET_BALANCE=ROOT_URL + "artist/my_wallet.php";
    public static final String URL_MY_DONATION_HISTORY=ROOT_URL + "artist/donation_history.php";
    //ArtWork
    public static final String URL_ART_WORK_GALLERY=ROOT_URL + "artist/art_work.php";
    //Exhibition
    public static final String URL_GET_EXHIBITIONS=ROOT_URL + "artist/exhibitions.php";
    public static final String URL_SUBMIT_EXHIBITION_ART = ROOT_URL + "artist/submit_artwork.php/";
    //Patron
    public static final String URL_PATRON_LOGIN= ROOT_URL+"patron/login.php";
    public static final String URL_DONATE=ROOT_URL + "patron/donate.php";
    public static final String URL_DONATE_EX_ARTWORKS=ROOT_URL + "patron/donate_exhibition_art.php";
    public static final String URL_DONATION_HISTORY=ROOT_URL + "patron/donation_history.php";
    //Exhibition Manager
    public static final String URL_CREATE_EXHIBITION = ROOT_URL + "exhibition_mrg/create_exhibition.php/";
    public static final String URL_UPCOMING_EVENTS=ROOT_URL + "exhibition_mrg/upcoming_events.php";
    public static final String URL_GET_APPLICANTS=ROOT_URL + "exhibition_mrg/exhibition_applicants.php";
    public static final String URL_APPROVE_ARTWORK=ROOT_URL + "exhibition_mrg/approve_artwork.php";
    public static final String URL_APPROVED_APPLICANTS=ROOT_URL + "exhibition_mrg/approved_applicants.php";
    public static final String URL_PENDING_ARTWORKS=ROOT_URL + "exhibition_mrg/pending_artworks.php";
    public static final String URL_APPROVE_ART_BLOG=ROOT_URL + "exhibition_mrg/approve_art_blog.php";
    public static final String URL_ADD_INVENTORY=ROOT_URL + "exhibition_mrg/add_inventory.php";
    public static final String URL_GET_ART_INVENTORY=ROOT_URL + "exhibition_mrg/art_inventory.php";
    //Mentor
    public static final String URL_CREATE_WORKSHOP = ROOT_URL + "mentor/create_workshop.php/";
    public static final String URL_MY_WORKSHOPS=ROOT_URL + "mentor/my_workshops.php";
    public static final String URL_START_WORKSHOP=ROOT_URL + "mentor/start_workshop.php";
    public static final String URL_COMPLETE_WORKSHOP=ROOT_URL + "mentor/complete_workshop.php";
    public static final String URL_WORKSHOP_REGISTRATIONS=ROOT_URL + "mentor/new_registrations.php";
    public static final String URL_APPROVE_STUDENT=ROOT_URL + "mentor/approve_student.php";
    public static final String URL_APPROVED_ATTENDANCE=ROOT_URL + "mentor/approved_attendance.php";
    public static final String URL_CHECK_WORKSHOP_STATUS=ROOT_URL + "mentor/check_status.php";
    //Students
    public static final String URL_WORKSHOPS=ROOT_URL + "students/workshops.php";
    public static final String URL_REG_WORKSHOP=ROOT_URL + "students/reg_workshop.php";
    public static final String URL_CHECK_STATUS=ROOT_URL + "students/check_status.php";

}