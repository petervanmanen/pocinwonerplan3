import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Besluittype from './besluittype';
import BesluittypeDetail from './besluittype-detail';
import BesluittypeUpdate from './besluittype-update';
import BesluittypeDeleteDialog from './besluittype-delete-dialog';

const BesluittypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Besluittype />} />
    <Route path="new" element={<BesluittypeUpdate />} />
    <Route path=":id">
      <Route index element={<BesluittypeDetail />} />
      <Route path="edit" element={<BesluittypeUpdate />} />
      <Route path="delete" element={<BesluittypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BesluittypeRoutes;
