import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sbiactiviteit from './sbiactiviteit';
import SbiactiviteitDetail from './sbiactiviteit-detail';
import SbiactiviteitUpdate from './sbiactiviteit-update';
import SbiactiviteitDeleteDialog from './sbiactiviteit-delete-dialog';

const SbiactiviteitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sbiactiviteit />} />
    <Route path="new" element={<SbiactiviteitUpdate />} />
    <Route path=":id">
      <Route index element={<SbiactiviteitDetail />} />
      <Route path="edit" element={<SbiactiviteitUpdate />} />
      <Route path="delete" element={<SbiactiviteitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SbiactiviteitRoutes;
