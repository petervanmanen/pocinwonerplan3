import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Subsidieaanvraag from './subsidieaanvraag';
import SubsidieaanvraagDetail from './subsidieaanvraag-detail';
import SubsidieaanvraagUpdate from './subsidieaanvraag-update';
import SubsidieaanvraagDeleteDialog from './subsidieaanvraag-delete-dialog';

const SubsidieaanvraagRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Subsidieaanvraag />} />
    <Route path="new" element={<SubsidieaanvraagUpdate />} />
    <Route path=":id">
      <Route index element={<SubsidieaanvraagDetail />} />
      <Route path="edit" element={<SubsidieaanvraagUpdate />} />
      <Route path="delete" element={<SubsidieaanvraagDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubsidieaanvraagRoutes;
