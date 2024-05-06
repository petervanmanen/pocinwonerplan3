import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Uitstroomreden from './uitstroomreden';
import UitstroomredenDetail from './uitstroomreden-detail';
import UitstroomredenUpdate from './uitstroomreden-update';
import UitstroomredenDeleteDialog from './uitstroomreden-delete-dialog';

const UitstroomredenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Uitstroomreden />} />
    <Route path="new" element={<UitstroomredenUpdate />} />
    <Route path=":id">
      <Route index element={<UitstroomredenDetail />} />
      <Route path="edit" element={<UitstroomredenUpdate />} />
      <Route path="delete" element={<UitstroomredenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UitstroomredenRoutes;
