import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Crowmelding from './crowmelding';
import CrowmeldingDetail from './crowmelding-detail';
import CrowmeldingUpdate from './crowmelding-update';
import CrowmeldingDeleteDialog from './crowmelding-delete-dialog';

const CrowmeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Crowmelding />} />
    <Route path="new" element={<CrowmeldingUpdate />} />
    <Route path=":id">
      <Route index element={<CrowmeldingDetail />} />
      <Route path="edit" element={<CrowmeldingUpdate />} />
      <Route path="delete" element={<CrowmeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CrowmeldingRoutes;
