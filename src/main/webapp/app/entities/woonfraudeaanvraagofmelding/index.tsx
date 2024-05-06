import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Woonfraudeaanvraagofmelding from './woonfraudeaanvraagofmelding';
import WoonfraudeaanvraagofmeldingDetail from './woonfraudeaanvraagofmelding-detail';
import WoonfraudeaanvraagofmeldingUpdate from './woonfraudeaanvraagofmelding-update';
import WoonfraudeaanvraagofmeldingDeleteDialog from './woonfraudeaanvraagofmelding-delete-dialog';

const WoonfraudeaanvraagofmeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Woonfraudeaanvraagofmelding />} />
    <Route path="new" element={<WoonfraudeaanvraagofmeldingUpdate />} />
    <Route path=":id">
      <Route index element={<WoonfraudeaanvraagofmeldingDetail />} />
      <Route path="edit" element={<WoonfraudeaanvraagofmeldingUpdate />} />
      <Route path="delete" element={<WoonfraudeaanvraagofmeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WoonfraudeaanvraagofmeldingRoutes;
