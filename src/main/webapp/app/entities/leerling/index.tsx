import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Leerling from './leerling';
import LeerlingDetail from './leerling-detail';
import LeerlingUpdate from './leerling-update';
import LeerlingDeleteDialog from './leerling-delete-dialog';

const LeerlingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Leerling />} />
    <Route path="new" element={<LeerlingUpdate />} />
    <Route path=":id">
      <Route index element={<LeerlingDetail />} />
      <Route path="edit" element={<LeerlingUpdate />} />
      <Route path="delete" element={<LeerlingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeerlingRoutes;
