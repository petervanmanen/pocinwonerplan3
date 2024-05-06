import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kpbetrokkenbij from './kpbetrokkenbij';
import KpbetrokkenbijDetail from './kpbetrokkenbij-detail';
import KpbetrokkenbijUpdate from './kpbetrokkenbij-update';
import KpbetrokkenbijDeleteDialog from './kpbetrokkenbij-delete-dialog';

const KpbetrokkenbijRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kpbetrokkenbij />} />
    <Route path="new" element={<KpbetrokkenbijUpdate />} />
    <Route path=":id">
      <Route index element={<KpbetrokkenbijDetail />} />
      <Route path="edit" element={<KpbetrokkenbijUpdate />} />
      <Route path="delete" element={<KpbetrokkenbijDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KpbetrokkenbijRoutes;
