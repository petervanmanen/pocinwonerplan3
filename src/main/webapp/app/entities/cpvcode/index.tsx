import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cpvcode from './cpvcode';
import CpvcodeDetail from './cpvcode-detail';
import CpvcodeUpdate from './cpvcode-update';
import CpvcodeDeleteDialog from './cpvcode-delete-dialog';

const CpvcodeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cpvcode />} />
    <Route path="new" element={<CpvcodeUpdate />} />
    <Route path=":id">
      <Route index element={<CpvcodeDetail />} />
      <Route path="edit" element={<CpvcodeUpdate />} />
      <Route path="delete" element={<CpvcodeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CpvcodeRoutes;
