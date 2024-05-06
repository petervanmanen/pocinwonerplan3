import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Uitvoerdergraafwerkzaamheden from './uitvoerdergraafwerkzaamheden';
import UitvoerdergraafwerkzaamhedenDetail from './uitvoerdergraafwerkzaamheden-detail';
import UitvoerdergraafwerkzaamhedenUpdate from './uitvoerdergraafwerkzaamheden-update';
import UitvoerdergraafwerkzaamhedenDeleteDialog from './uitvoerdergraafwerkzaamheden-delete-dialog';

const UitvoerdergraafwerkzaamhedenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Uitvoerdergraafwerkzaamheden />} />
    <Route path="new" element={<UitvoerdergraafwerkzaamhedenUpdate />} />
    <Route path=":id">
      <Route index element={<UitvoerdergraafwerkzaamhedenDetail />} />
      <Route path="edit" element={<UitvoerdergraafwerkzaamhedenUpdate />} />
      <Route path="delete" element={<UitvoerdergraafwerkzaamhedenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UitvoerdergraafwerkzaamhedenRoutes;
