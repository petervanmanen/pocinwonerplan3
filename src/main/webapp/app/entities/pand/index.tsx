import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Pand from './pand';
import PandDetail from './pand-detail';
import PandUpdate from './pand-update';
import PandDeleteDialog from './pand-delete-dialog';

const PandRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Pand />} />
    <Route path="new" element={<PandUpdate />} />
    <Route path=":id">
      <Route index element={<PandDetail />} />
      <Route path="edit" element={<PandUpdate />} />
      <Route path="delete" element={<PandDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PandRoutes;
