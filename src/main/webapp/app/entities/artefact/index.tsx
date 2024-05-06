import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Artefact from './artefact';
import ArtefactDetail from './artefact-detail';
import ArtefactUpdate from './artefact-update';
import ArtefactDeleteDialog from './artefact-delete-dialog';

const ArtefactRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Artefact />} />
    <Route path="new" element={<ArtefactUpdate />} />
    <Route path=":id">
      <Route index element={<ArtefactDetail />} />
      <Route path="edit" element={<ArtefactUpdate />} />
      <Route path="delete" element={<ArtefactDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtefactRoutes;
