import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Onbestemdadres from './onbestemdadres';
import OnbestemdadresDetail from './onbestemdadres-detail';
import OnbestemdadresUpdate from './onbestemdadres-update';
import OnbestemdadresDeleteDialog from './onbestemdadres-delete-dialog';

const OnbestemdadresRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Onbestemdadres />} />
    <Route path="new" element={<OnbestemdadresUpdate />} />
    <Route path=":id">
      <Route index element={<OnbestemdadresDetail />} />
      <Route path="edit" element={<OnbestemdadresUpdate />} />
      <Route path="delete" element={<OnbestemdadresDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OnbestemdadresRoutes;
