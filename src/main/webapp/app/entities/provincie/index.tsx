import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Provincie from './provincie';
import ProvincieDetail from './provincie-detail';
import ProvincieUpdate from './provincie-update';
import ProvincieDeleteDialog from './provincie-delete-dialog';

const ProvincieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Provincie />} />
    <Route path="new" element={<ProvincieUpdate />} />
    <Route path=":id">
      <Route index element={<ProvincieDetail />} />
      <Route path="edit" element={<ProvincieUpdate />} />
      <Route path="delete" element={<ProvincieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProvincieRoutes;
