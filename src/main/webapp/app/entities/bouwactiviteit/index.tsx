import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bouwactiviteit from './bouwactiviteit';
import BouwactiviteitDetail from './bouwactiviteit-detail';
import BouwactiviteitUpdate from './bouwactiviteit-update';
import BouwactiviteitDeleteDialog from './bouwactiviteit-delete-dialog';

const BouwactiviteitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bouwactiviteit />} />
    <Route path="new" element={<BouwactiviteitUpdate />} />
    <Route path=":id">
      <Route index element={<BouwactiviteitDetail />} />
      <Route path="edit" element={<BouwactiviteitUpdate />} />
      <Route path="delete" element={<BouwactiviteitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BouwactiviteitRoutes;
