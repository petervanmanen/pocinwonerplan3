import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kunstwerk from './kunstwerk';
import KunstwerkDetail from './kunstwerk-detail';
import KunstwerkUpdate from './kunstwerk-update';
import KunstwerkDeleteDialog from './kunstwerk-delete-dialog';

const KunstwerkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kunstwerk />} />
    <Route path="new" element={<KunstwerkUpdate />} />
    <Route path=":id">
      <Route index element={<KunstwerkDetail />} />
      <Route path="edit" element={<KunstwerkUpdate />} />
      <Route path="delete" element={<KunstwerkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KunstwerkRoutes;
