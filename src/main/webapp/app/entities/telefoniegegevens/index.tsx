import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Telefoniegegevens from './telefoniegegevens';
import TelefoniegegevensDetail from './telefoniegegevens-detail';
import TelefoniegegevensUpdate from './telefoniegegevens-update';
import TelefoniegegevensDeleteDialog from './telefoniegegevens-delete-dialog';

const TelefoniegegevensRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Telefoniegegevens />} />
    <Route path="new" element={<TelefoniegegevensUpdate />} />
    <Route path=":id">
      <Route index element={<TelefoniegegevensDetail />} />
      <Route path="edit" element={<TelefoniegegevensUpdate />} />
      <Route path="delete" element={<TelefoniegegevensDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TelefoniegegevensRoutes;
