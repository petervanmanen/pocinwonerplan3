import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Offerte from './offerte';
import OfferteDetail from './offerte-detail';
import OfferteUpdate from './offerte-update';
import OfferteDeleteDialog from './offerte-delete-dialog';

const OfferteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Offerte />} />
    <Route path="new" element={<OfferteUpdate />} />
    <Route path=":id">
      <Route index element={<OfferteDetail />} />
      <Route path="edit" element={<OfferteUpdate />} />
      <Route path="delete" element={<OfferteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OfferteRoutes;
