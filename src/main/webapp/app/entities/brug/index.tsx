import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Brug from './brug';
import BrugDetail from './brug-detail';
import BrugUpdate from './brug-update';
import BrugDeleteDialog from './brug-delete-dialog';

const BrugRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Brug />} />
    <Route path="new" element={<BrugUpdate />} />
    <Route path=":id">
      <Route index element={<BrugDetail />} />
      <Route path="edit" element={<BrugUpdate />} />
      <Route path="delete" element={<BrugDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BrugRoutes;
