import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Combibon from './combibon';
import CombibonDetail from './combibon-detail';
import CombibonUpdate from './combibon-update';
import CombibonDeleteDialog from './combibon-delete-dialog';

const CombibonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Combibon />} />
    <Route path="new" element={<CombibonUpdate />} />
    <Route path=":id">
      <Route index element={<CombibonDetail />} />
      <Route path="edit" element={<CombibonUpdate />} />
      <Route path="delete" element={<CombibonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CombibonRoutes;
