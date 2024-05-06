import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gemeente from './gemeente';
import GemeenteDetail from './gemeente-detail';
import GemeenteUpdate from './gemeente-update';
import GemeenteDeleteDialog from './gemeente-delete-dialog';

const GemeenteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gemeente />} />
    <Route path="new" element={<GemeenteUpdate />} />
    <Route path=":id">
      <Route index element={<GemeenteDetail />} />
      <Route path="edit" element={<GemeenteUpdate />} />
      <Route path="delete" element={<GemeenteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GemeenteRoutes;
