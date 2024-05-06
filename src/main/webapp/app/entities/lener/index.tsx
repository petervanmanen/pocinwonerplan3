import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Lener from './lener';
import LenerDetail from './lener-detail';
import LenerUpdate from './lener-update';
import LenerDeleteDialog from './lener-delete-dialog';

const LenerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Lener />} />
    <Route path="new" element={<LenerUpdate />} />
    <Route path=":id">
      <Route index element={<LenerDetail />} />
      <Route path="edit" element={<LenerUpdate />} />
      <Route path="delete" element={<LenerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LenerRoutes;
