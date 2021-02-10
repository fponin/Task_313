function openDeleteModal(id) {
    $.ajax({
        url: "/admin/delete-user/" + id,
        success: function (data) {
            $("#deleteUserModalHolder").html(data);
            $('#deleteUserModal').modal("show");

        }
    });
}

function openEditModal(id) {
    $.ajax({
        url: "/admin/edit-user/" + id,
        success: function (data) {
            $("#editUserModalHolder").html(data);
            $('#editUserModal').modal("show");
        }
    });
}