import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nummeraanduiding from './nummeraanduiding';
import NummeraanduidingDetail from './nummeraanduiding-detail';
import NummeraanduidingUpdate from './nummeraanduiding-update';
import NummeraanduidingDeleteDialog from './nummeraanduiding-delete-dialog';

const NummeraanduidingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Nummeraanduiding />} />
    <Route path="new" element={<NummeraanduidingUpdate />} />
    <Route path=":id">
      <Route index element={<NummeraanduidingDetail />} />
      <Route path="edit" element={<NummeraanduidingUpdate />} />
      <Route path="delete" element={<NummeraanduidingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NummeraanduidingRoutes;
