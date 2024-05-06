import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vaartuig from './vaartuig';
import VaartuigDetail from './vaartuig-detail';
import VaartuigUpdate from './vaartuig-update';
import VaartuigDeleteDialog from './vaartuig-delete-dialog';

const VaartuigRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vaartuig />} />
    <Route path="new" element={<VaartuigUpdate />} />
    <Route path=":id">
      <Route index element={<VaartuigDetail />} />
      <Route path="edit" element={<VaartuigUpdate />} />
      <Route path="delete" element={<VaartuigDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VaartuigRoutes;
