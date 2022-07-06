package com.sts.ssms.data.helpdesk.features.repository

class FeaturesLocalDataSource {

    fun featuresRawString() =
        """
            <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><strong>Conversations &amp; Groups</strong>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>A resident can form group and interact with other residents in society.</li>
                    <li>Residents in groups can discuss society related improvements and suggestions.</li>
                    <li>Conversations is visible to all residents in society.</li>
                    <li>Encourages active participation of members which leads to betterment of society.</li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li> <strong>Documents &amp; Photo Gallery</strong>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>Storing place for all vital documents and photos.</li>
                    <li>Documents are categorized into folders.</li>
                    <li>Resident can create folder/album and upload documents/photos.</li>
                    <li>Documents and photos are accessible to all the residents of that society.</li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><strong>Events &amp; Notices</strong>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>Grouping of all notices is done by “AGM”, “General”, “Special AGM”.</li>
                    <li>A resident can add notice and set expiry date for the same.</li>
                    <li>Major reports and events can be published on notice board with single click.</li>
                    <li>Expired notices can be viewed in old notice section.</li>
                    <li>Events section provides calendar in which important events can be marked for future references.</li>
                    <li>Resident can add new activity for a particular date.</li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><strong>Helpdesk</strong>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>A resident can lodge complaint tickets.</li>
                    <li>Here, residents can raise issues related to their flats and society in general.</li>
                    <li>Ticket which require immediate action can be marked as urgent.</li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><b>Users &amp; Blocks</b>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>Admin can access user information in this section.</li>
                    <li>Resident accounts can be added, approved and deactivated by admin.<i class="fa-li fa fa-circle"></i></li>
                    <li>Search on any family information, flat number, block number, mobile number etc.</li>
                    <li>Admin can view and add blocks in Blocks section.</li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><strong>Admin Files</strong>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>Storing place for scanned agreements, important manuals, work in progress documents, receipts of various facilities in society.</li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><b>Forums</b>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>Admin forum is available only to the admin team.<i class="fa-li fa fa-circle"></i></li>
                    <li>Same features as that of the Conversations &amp; Groups in resident section.<i class="fa-li fa fa-circle"></i></li>
                    <li>Discuss facility management issues and other improvement areas within the admin team.</li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><strong>Meetings &amp; task</strong>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>Admin can create meetings to discuss important topics related to society issues and concerns.</li>
                    <li>Email notifications are sent to the residents.<i class="fa-li fa fa-circle"></i></li>
                    <li>Ensures continuity and development of work.</li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><strong>Parking</strong>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>Admin can add  parking in this section by entering the building name and category of parking.</li>
                    <li>Status regarding the parking is available or not can be easily identified.</li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><strong>Amenities</strong>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>Amenities can be added by admin according to the requirement.</li>
                    <li>Amenities are grouped on the basis of shareable &amp; non-shareable.</li>
                    <li>The resident can request for booking the amenity and admin can approve and disapprove the request.</li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><strong>Billing</strong>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>Admin can add bill items (e.g.- Water Charges,Parking Charges,Municipal Taxes,Maintenance Fee) and generate bills for regular,fixed charges.</li>
                    <li>Admin can generate the bill.</li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><strong>Accounting</strong>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>Accounting is further sub-divided into
                    <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                        <li>Income Tracker</li>
                        <li>Other Income Tracker</li>
                        <li>Expense Tracker</li>
                    </ul>
                    </li>
                </ul>
                </li>
            </ul>
            <ul style="Margin:0; Margin-top: 16px; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                <li><strong>Vendor &amp; Staff Management</strong>
                <ul style="Margin:0; Margin-left: 25px; padding:0; font-family: Arial, Helvetica, sans-serif;" type="disc">
                    <li>Vendor/Staff can be added by both admin &amp; resident.</li>
                    <li>Admin can view all the added vendors/satff and has the right to approve or disapprove them.</li>
                    <li>Resident can only view approved one by admin and the vendor/staff created by them.</li>
                </ul>
                </li>
            </ul>
    """.trimMargin()

}