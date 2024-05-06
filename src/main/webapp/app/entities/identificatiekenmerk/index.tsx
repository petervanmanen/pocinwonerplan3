import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Identificatiekenmerk from './identificatiekenmerk';
import IdentificatiekenmerkDetail from './identificatiekenmerk-detail';
import IdentificatiekenmerkUpdate from './identificatiekenmerk-update';
import IdentificatiekenmerkDeleteDialog from './identificatiekenmerk-delete-dialog';

const IdentificatiekenmerkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Identificatiekenmerk />} />
    <Route path="new" element={<IdentificatiekenmerkUpdate />} />
    <Route path=":id">
      <Route index element={<IdentificatiekenmerkDetail />} />
      <Route path="edit" element={<IdentificatiekenmerkUpdate />} />
      <Route path="delete" element={<IdentificatiekenmerkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IdentificatiekenmerkRoutes;
