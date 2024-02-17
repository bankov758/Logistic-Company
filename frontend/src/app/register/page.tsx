import Link from "next/link";

const RegistrationPage = () => {
    return <div  className="bg-white p-40 rounded-xl shadow-md text-center">
        <div className="mb-5">
            <h2>Register</h2>
        </div>

        <form>
        <div className="input-group">
              <label className="block">Username</label>
              <input type="text" name="username" className="block w-full rounded-xl border-0 py-1.5  ring-1 ring-inset ring-gray-400 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
          </div>
            <div className="input-group">
                <label className="block">Email</label>
                <input type="email" name="email"  className="block w-full rounded-xl border-0 py-1.5  ring-1 ring-inset ring-gray-400 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
            </div>
            <div className="input-group">
                <label className="block">Password</label>
                <input type="password" name="password_1"  className="block w-full rounded-xl border-0 py-1.5  ring-1 ring-inset ring-gray-400 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
            </div>
            <div className="input-group">
                <label className="block">Confirm password</label>
                <input type="password" name="password_2"  className="input-style"></input>
            </div>
            <div className="flex justify-center">
                <button type="submit" className="block px-3 py-1.5 mt-1  relative rounded-xl bg-green-400 text-white border-none cursor-pointer" name="reg_user">Register</button>
            </div>
            <p>
                Already a member? <Link href="/login"> Sign in </Link>
            </p>
        </form>
    </div>
}

export default RegistrationPage;