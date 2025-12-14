export async function getStaffUsers() {
  const response = await fetch("/api/v1/dashboard/users/staff", {
    method: "GET",
    credentials: "include"
  });

  if (!response.ok) {
    throw new Error("Could not fetch staff users");
  }

  return await response.json();
}
