import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Uren from './uren';
import UrenDetail from './uren-detail';
import UrenUpdate from './uren-update';
import UrenDeleteDialog from './uren-delete-dialog';

const UrenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Uren />} />
    <Route path="new" element={<UrenUpdate />} />
    <Route path=":id">
      <Route index element={<UrenDetail />} />
      <Route path="edit" element={<UrenUpdate />} />
      <Route path="delete" element={<UrenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UrenRoutes;
