import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eobjecttypef from './eobjecttypef';
import EobjecttypefDetail from './eobjecttypef-detail';
import EobjecttypefUpdate from './eobjecttypef-update';
import EobjecttypefDeleteDialog from './eobjecttypef-delete-dialog';

const EobjecttypefRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eobjecttypef />} />
    <Route path="new" element={<EobjecttypefUpdate />} />
    <Route path=":id">
      <Route index element={<EobjecttypefDetail />} />
      <Route path="edit" element={<EobjecttypefUpdate />} />
      <Route path="delete" element={<EobjecttypefDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EobjecttypefRoutes;
