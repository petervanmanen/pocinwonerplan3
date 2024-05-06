import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Leerplichtambtenaar from './leerplichtambtenaar';
import LeerplichtambtenaarDetail from './leerplichtambtenaar-detail';
import LeerplichtambtenaarUpdate from './leerplichtambtenaar-update';
import LeerplichtambtenaarDeleteDialog from './leerplichtambtenaar-delete-dialog';

const LeerplichtambtenaarRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Leerplichtambtenaar />} />
    <Route path="new" element={<LeerplichtambtenaarUpdate />} />
    <Route path=":id">
      <Route index element={<LeerplichtambtenaarDetail />} />
      <Route path="edit" element={<LeerplichtambtenaarUpdate />} />
      <Route path="delete" element={<LeerplichtambtenaarDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeerplichtambtenaarRoutes;
