import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bezoeker from './bezoeker';
import BezoekerDetail from './bezoeker-detail';
import BezoekerUpdate from './bezoeker-update';
import BezoekerDeleteDialog from './bezoeker-delete-dialog';

const BezoekerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bezoeker />} />
    <Route path="new" element={<BezoekerUpdate />} />
    <Route path=":id">
      <Route index element={<BezoekerDetail />} />
      <Route path="edit" element={<BezoekerUpdate />} />
      <Route path="delete" element={<BezoekerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BezoekerRoutes;
