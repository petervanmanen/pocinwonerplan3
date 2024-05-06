import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Elog from './elog';
import ElogDetail from './elog-detail';
import ElogUpdate from './elog-update';
import ElogDeleteDialog from './elog-delete-dialog';

const ElogRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Elog />} />
    <Route path="new" element={<ElogUpdate />} />
    <Route path=":id">
      <Route index element={<ElogDetail />} />
      <Route path="edit" element={<ElogUpdate />} />
      <Route path="delete" element={<ElogDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ElogRoutes;
