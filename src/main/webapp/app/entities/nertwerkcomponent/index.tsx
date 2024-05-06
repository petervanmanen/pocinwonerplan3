import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nertwerkcomponent from './nertwerkcomponent';
import NertwerkcomponentDetail from './nertwerkcomponent-detail';
import NertwerkcomponentUpdate from './nertwerkcomponent-update';
import NertwerkcomponentDeleteDialog from './nertwerkcomponent-delete-dialog';

const NertwerkcomponentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Nertwerkcomponent />} />
    <Route path="new" element={<NertwerkcomponentUpdate />} />
    <Route path=":id">
      <Route index element={<NertwerkcomponentDetail />} />
      <Route path="edit" element={<NertwerkcomponentUpdate />} />
      <Route path="delete" element={<NertwerkcomponentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NertwerkcomponentRoutes;
