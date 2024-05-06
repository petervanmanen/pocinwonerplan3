import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Opleiding from './opleiding';
import OpleidingDetail from './opleiding-detail';
import OpleidingUpdate from './opleiding-update';
import OpleidingDeleteDialog from './opleiding-delete-dialog';

const OpleidingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Opleiding />} />
    <Route path="new" element={<OpleidingUpdate />} />
    <Route path=":id">
      <Route index element={<OpleidingDetail />} />
      <Route path="edit" element={<OpleidingUpdate />} />
      <Route path="delete" element={<OpleidingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OpleidingRoutes;
