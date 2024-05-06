import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kosten from './kosten';
import KostenDetail from './kosten-detail';
import KostenUpdate from './kosten-update';
import KostenDeleteDialog from './kosten-delete-dialog';

const KostenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kosten />} />
    <Route path="new" element={<KostenUpdate />} />
    <Route path=":id">
      <Route index element={<KostenDetail />} />
      <Route path="edit" element={<KostenUpdate />} />
      <Route path="delete" element={<KostenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KostenRoutes;
