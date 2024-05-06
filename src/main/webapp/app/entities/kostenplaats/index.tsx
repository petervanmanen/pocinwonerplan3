import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kostenplaats from './kostenplaats';
import KostenplaatsDetail from './kostenplaats-detail';
import KostenplaatsUpdate from './kostenplaats-update';
import KostenplaatsDeleteDialog from './kostenplaats-delete-dialog';

const KostenplaatsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kostenplaats />} />
    <Route path="new" element={<KostenplaatsUpdate />} />
    <Route path=":id">
      <Route index element={<KostenplaatsDetail />} />
      <Route path="edit" element={<KostenplaatsUpdate />} />
      <Route path="delete" element={<KostenplaatsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KostenplaatsRoutes;
