import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beperking from './beperking';
import BeperkingDetail from './beperking-detail';
import BeperkingUpdate from './beperking-update';
import BeperkingDeleteDialog from './beperking-delete-dialog';

const BeperkingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beperking />} />
    <Route path="new" element={<BeperkingUpdate />} />
    <Route path=":id">
      <Route index element={<BeperkingDetail />} />
      <Route path="edit" element={<BeperkingUpdate />} />
      <Route path="delete" element={<BeperkingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeperkingRoutes;
