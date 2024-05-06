import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Soortscheiding from './soortscheiding';
import SoortscheidingDetail from './soortscheiding-detail';
import SoortscheidingUpdate from './soortscheiding-update';
import SoortscheidingDeleteDialog from './soortscheiding-delete-dialog';

const SoortscheidingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Soortscheiding />} />
    <Route path="new" element={<SoortscheidingUpdate />} />
    <Route path=":id">
      <Route index element={<SoortscheidingDetail />} />
      <Route path="edit" element={<SoortscheidingUpdate />} />
      <Route path="delete" element={<SoortscheidingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SoortscheidingRoutes;
