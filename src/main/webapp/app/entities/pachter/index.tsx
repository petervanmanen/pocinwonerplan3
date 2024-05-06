import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Pachter from './pachter';
import PachterDetail from './pachter-detail';
import PachterUpdate from './pachter-update';
import PachterDeleteDialog from './pachter-delete-dialog';

const PachterRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Pachter />} />
    <Route path="new" element={<PachterUpdate />} />
    <Route path=":id">
      <Route index element={<PachterDetail />} />
      <Route path="edit" element={<PachterUpdate />} />
      <Route path="delete" element={<PachterDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PachterRoutes;
