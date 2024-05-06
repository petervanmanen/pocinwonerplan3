import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Koopwoningen from './koopwoningen';
import KoopwoningenDetail from './koopwoningen-detail';
import KoopwoningenUpdate from './koopwoningen-update';
import KoopwoningenDeleteDialog from './koopwoningen-delete-dialog';

const KoopwoningenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Koopwoningen />} />
    <Route path="new" element={<KoopwoningenUpdate />} />
    <Route path=":id">
      <Route index element={<KoopwoningenDetail />} />
      <Route path="edit" element={<KoopwoningenUpdate />} />
      <Route path="delete" element={<KoopwoningenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KoopwoningenRoutes;
