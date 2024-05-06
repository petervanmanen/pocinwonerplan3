import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Offerteaanvraag from './offerteaanvraag';
import OfferteaanvraagDetail from './offerteaanvraag-detail';
import OfferteaanvraagUpdate from './offerteaanvraag-update';
import OfferteaanvraagDeleteDialog from './offerteaanvraag-delete-dialog';

const OfferteaanvraagRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Offerteaanvraag />} />
    <Route path="new" element={<OfferteaanvraagUpdate />} />
    <Route path=":id">
      <Route index element={<OfferteaanvraagDetail />} />
      <Route path="edit" element={<OfferteaanvraagUpdate />} />
      <Route path="delete" element={<OfferteaanvraagDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OfferteaanvraagRoutes;
