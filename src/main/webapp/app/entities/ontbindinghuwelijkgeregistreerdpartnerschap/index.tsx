import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ontbindinghuwelijkgeregistreerdpartnerschap from './ontbindinghuwelijkgeregistreerdpartnerschap';
import OntbindinghuwelijkgeregistreerdpartnerschapDetail from './ontbindinghuwelijkgeregistreerdpartnerschap-detail';
import OntbindinghuwelijkgeregistreerdpartnerschapUpdate from './ontbindinghuwelijkgeregistreerdpartnerschap-update';
import OntbindinghuwelijkgeregistreerdpartnerschapDeleteDialog from './ontbindinghuwelijkgeregistreerdpartnerschap-delete-dialog';

const OntbindinghuwelijkgeregistreerdpartnerschapRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ontbindinghuwelijkgeregistreerdpartnerschap />} />
    <Route path="new" element={<OntbindinghuwelijkgeregistreerdpartnerschapUpdate />} />
    <Route path=":id">
      <Route index element={<OntbindinghuwelijkgeregistreerdpartnerschapDetail />} />
      <Route path="edit" element={<OntbindinghuwelijkgeregistreerdpartnerschapUpdate />} />
      <Route path="delete" element={<OntbindinghuwelijkgeregistreerdpartnerschapDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OntbindinghuwelijkgeregistreerdpartnerschapRoutes;
