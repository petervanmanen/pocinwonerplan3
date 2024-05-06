import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ecomponent from './ecomponent';
import EcomponentDetail from './ecomponent-detail';
import EcomponentUpdate from './ecomponent-update';
import EcomponentDeleteDialog from './ecomponent-delete-dialog';

const EcomponentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ecomponent />} />
    <Route path="new" element={<EcomponentUpdate />} />
    <Route path=":id">
      <Route index element={<EcomponentDetail />} />
      <Route path="edit" element={<EcomponentUpdate />} />
      <Route path="delete" element={<EcomponentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EcomponentRoutes;
