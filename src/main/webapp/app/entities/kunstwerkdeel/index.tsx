import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kunstwerkdeel from './kunstwerkdeel';
import KunstwerkdeelDetail from './kunstwerkdeel-detail';
import KunstwerkdeelUpdate from './kunstwerkdeel-update';
import KunstwerkdeelDeleteDialog from './kunstwerkdeel-delete-dialog';

const KunstwerkdeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kunstwerkdeel />} />
    <Route path="new" element={<KunstwerkdeelUpdate />} />
    <Route path=":id">
      <Route index element={<KunstwerkdeelDetail />} />
      <Route path="edit" element={<KunstwerkdeelUpdate />} />
      <Route path="delete" element={<KunstwerkdeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KunstwerkdeelRoutes;
