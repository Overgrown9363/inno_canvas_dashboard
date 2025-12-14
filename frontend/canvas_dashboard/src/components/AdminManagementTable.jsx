function AdminManagementTable({
    staffUsers,
    staffLoading,
    staffError,
}) {
    return (
        <div className="admin-management-group">
            <h2>Beheer Gebruikers</h2>
            {staffLoading && <div>Loading staff users...</div>}
            {staffError && <div>Error: {staffError}</div>}
            {!staffLoading && !staffError && (
                <table>
                    <thead>
                        <tr>
                            <th>Naam</th>
                            <th>Email</th>
                            <th>Rol</th>
                        </tr>
                    </thead>
                    <tbody>
                        {staffUsers.map((user) => (
                            <tr key={user.email}>
                                <td>{user.name}</td>
                                <td>{user.email}</td>
                                <td>{user.role}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default AdminManagementTable;
