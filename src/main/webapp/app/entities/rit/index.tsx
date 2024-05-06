import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rit from './rit';
import RitDetail from './rit-detail';
import RitUpdate from './rit-update';
import RitDeleteDialog from './rit-delete-dialog';

const RitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rit />} />
    <Route path="new" element={<RitUpdate />} />
    <Route path=":id">
      <Route index element={<RitDetail />} />
      <Route path="edit" element={<RitUpdate />} />
      <Route path="delete" element={<RitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RitRoutes;
